package jpabook.jpashop;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static jpabook.jpashop.domain.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class QuerydslBasicTests {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory  = new JPAQueryFactory(em);
    }

    @Test
    public void sort() {
        Member member5 = new Member();
        member5.setName("member5");
        em.persist(member5);

        Member member6 = new Member();
        member6.setName("member6");
        em.persist(member6);

        Member memberNull = new Member();
        memberNull.setName(null);
        em.persist(memberNull);

        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.name.asc().nullsLast())
                .fetch();

        Member result5 = result.get(0);
        Member result6 = result.get(1);
        Member resultNull = result.get(2);

        assertThat(result5.getName()).isEqualTo(member5.getName());
        assertThat(result6.getName()).isEqualTo(member6.getName());
        assertThat(resultNull.getName()).isEqualTo(memberNull.getName());
    }

    @Test
    public void paging1(){
        Member member5 = new Member();
        member5.setName("member5");
        em.persist(member5);

        Member member6 = new Member();
        member6.setName("member6");
        em.persist(member6);

        Member member7 = new Member();
        member7.setName("member7");
        em.persist(member7);

        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.name.asc())
                .offset(1)
                .limit(2)
                .fetch();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo(member6.getName());
        assertThat(result.get(1).getName()).isEqualTo(member7.getName());
    }
    @Test
    public void paging2(){
        Member member5 = new Member();
        member5.setName("member5");
        em.persist(member5);

        Member member6 = new Member();
        member6.setName("member6");
        em.persist(member6);

        Member member7 = new Member();
        member7.setName("member7");
        em.persist(member7);

        QueryResults<Member> results = queryFactory
                .selectFrom(member)
                .orderBy(member.name.asc())
                .offset(1)
                .limit(2)
                .fetchResults();

        long total = results.getTotal();
        List<Member> result = results.getResults();

        assertThat(total).isEqualTo(3);
        assertThat(result.get(0).getName()).isEqualTo(member6.getName());
        assertThat(result.get(1).getName()).isEqualTo(member7.getName());
    }
}
