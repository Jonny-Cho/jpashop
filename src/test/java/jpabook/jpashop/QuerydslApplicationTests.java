package jpabook.jpashop;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.QMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class QuerydslApplicationTests {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory = new JPAQueryFactory(em);

    @Test
    public void contextLoads(){
        //given
        Member member = new Member();
        member.setName("jonny");
        em.persist(member);

        QMember qMember = QMember.member;
        //when

        Member result = queryFactory
                .selectFrom(qMember)
                .fetchOne();

        System.out.println(result);

        //then
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void startQuerydsl(){
        //given
        Member member = new Member();
        member.setName("member1");
        em.persist(member);

        //when
        QMember m = QMember.member;

        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.name.eq("member1"))
                .fetchOne();

        //then
        assertThat(findMember.getName()).isEqualTo("member1");
    }

}
