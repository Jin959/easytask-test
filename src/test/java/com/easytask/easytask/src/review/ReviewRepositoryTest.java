package com.easytask.easytask.src.review;

import com.easytask.easytask.src.review.repository.ReviewRepository;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskUserMapping;
import com.easytask.easytask.src.user.UserController;
import com.easytask.easytask.src.user.UserService;
import com.easytask.easytask.src.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserController userController;
    @Autowired
    UserService userService;
    @Autowired
    EntityManager entityManager;



    @Test
    @Transactional
    @Commit
    public void 리뷰_생성() {
        User user = new User("whdghrkwhr12@naver.com", "최종호", "01085738206");
        entityManager.persist(user);

        User irumi = new User("email@irumi.com","이루미1", "01011111111");
        entityManager.persist(irumi);

        User irumi2 = new User("email2@irumi2.com", "이루미2", "01022222222");
        entityManager.persist(irumi2);

        User irumi3 = new User("email3@irumi3.com", "이루미3", "01033333333");
        entityManager.persist(irumi3);

        Task task = new Task(user, "번역 업무", "영어 번역", "문서 작업", "워드스킬");
        taskRepository.save(task);

        TaskUserMapping taskUserMapping = new TaskUserMapping(irumi,task);
        entityManager.persist(taskUserMapping);

        TaskUserMapping taskUserMapping2 = new TaskUserMapping(irumi2,task);
        entityManager.persist(taskUserMapping2);

        TaskUserMapping taskUserMapping3 = new TaskUserMapping(irumi3,task);
        entityManager.persist(taskUserMapping3);

        RelatedAbility relatedAbility = new RelatedAbility(task,"문서번역", "영어문서번역");
        entityManager.persist(relatedAbility);

        task.addIrumiList(taskUserMapping);
        task.addIrumiList(taskUserMapping2);
        task.addIrumiList(taskUserMapping3);
        task.addRelatedAbilityList(relatedAbility);

//        Rating rating = new Rating();
    }
}