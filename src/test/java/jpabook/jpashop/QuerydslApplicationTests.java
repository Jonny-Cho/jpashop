package jpabook.jpashop;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.QMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static jpabook.jpashop.domain.QMember.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class QuerydslApplicationTests {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @Test
    public void contextLoads(){
        //given
        Member member = new Member();
        member.setName("jonny");
        em.persist(member);

        queryFactory  = new JPAQueryFactory(em);
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
        Member m = new Member();
        m.setName("member1");
        em.persist(m);

        queryFactory  = new JPAQueryFactory(em);

        //when
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.name.eq("member1"))
                .fetchOne();

        //then
        assertThat(findMember.getName()).isEqualTo("member1");
    }

}
