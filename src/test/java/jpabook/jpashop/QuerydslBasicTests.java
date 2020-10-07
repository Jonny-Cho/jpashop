package jpabook.jpashop;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Member;
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

    @Test
    public void sort() {
        queryFactory  = new JPAQueryFactory(em);

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

}
