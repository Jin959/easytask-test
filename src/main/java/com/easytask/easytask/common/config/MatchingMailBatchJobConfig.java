package com.easytask.easytask.common.config;

import com.easytask.easytask.common.scheduler.MatchingRequest;
import com.easytask.easytask.common.util.MailGenerator;
import com.easytask.easytask.common.util.MailService;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskMail;
import com.easytask.easytask.src.task.repository.TaskMailRepository;
import com.easytask.easytask.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MatchingMailBatchJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MatchingRequest matchingRequest;
    private final DataSource dataSource;
    private final ApplicationContext applicationContext;
    private final TaskMailRepository taskMailRepository;
    private final MailService mailService;
    private int CHUNK_SIZE=20;

    @Bean
    public Job matchingMailJob() throws Exception{
        return jobBuilderFactory.get("matchingMailJob")
                .preventRestart()
                .start(matchingMailStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step matchingMailStep(@Value("#{jobParameters[requestDate]}") final String requestDate) throws Exception {
        log.info("matchingMailStep requested at : requestDate = {}", requestDate);
        return stepBuilderFactory.get("matchingMailStep")
                .<User, TaskMail>chunk(CHUNK_SIZE)
                .reader(matchingMailReader())
                .processor(matchingMailProcessor())
                .writer(matchingMailWriter())
                .build();
    }

    @Bean
    @StepScope
    public MyBatisPagingItemReader<User> matchingMailReader() throws Exception{
        Task task = matchingRequest.getScheduledTask();
        List<RelatedAbility> relatedAbilityList = task.getRelatedAbilityList();

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("taskId", task.getId());
        parameterValues.put("taskCategorySmall", task.getCategorySmall());
        parameterValues.put("relatedAbilityList", relatedAbilityList);
        parameterValues.put("abilityCount", relatedAbilityList.size());

        return new MyBatisPagingItemReaderBuilder<User>()
                .sqlSessionFactory(sqlSessionFactory().getObject())
                .queryId("UserMapper.findUserOnMatchingMailBatch")
                .parameterValues(parameterValues)
                .pageSize(CHUNK_SIZE)
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<User, TaskMail> matchingMailProcessor() {
        return new ItemProcessor<User, TaskMail>() {
            @Override
            public TaskMail process(User irumi) throws Exception {
                Task task = matchingRequest.getScheduledTask();

                mailService.sendMatchingMail(new MailGenerator().createMatchingInvitation(task, irumi));

                return TaskMail.builder()
                        .irumi(irumi)
                        .task(task)
                        .build();
            }
        };
    }

    @Bean
    @StepScope
    public ItemWriter<TaskMail> matchingMailWriter() {
        return (List<? extends TaskMail> taskMailList) -> taskMailRepository.saveAll(taskMailList);
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/*.xml"));
        return sessionFactory;
    }
}
