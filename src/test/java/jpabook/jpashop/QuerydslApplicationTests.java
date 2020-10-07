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

import java.util.List;

import static jpabook.jpashop.domain.QMember.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class QuerydslApplicationTests {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before(){
        Member m = new Member();
        m.setName("member1");
        em.persist(m);

        queryFactory  = new JPAQueryFactory(em);
    }

    @Test
    public void contextLoads(){
        //when
        Member result = queryFactory
                .selectFrom(member)
                .fetchOne();

        System.out.println(result);

        //then
        assertThat(result.getName()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl(){
        //when
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.name.eq("member1"))
                .fetchOne();

        //then
        assertThat(findMember.getName()).isEqualTo("member1");
    }

    @Test
    public void search() {
        Member findMember = queryFactory
                .selectFrom(member)
                .where(
                    member.name.eq("member1"),
                    member.id.eq(1L)
                )
                .fetchOne();

        assertThat(findMember.getName()).isEqualTo("member1");
    }

}
