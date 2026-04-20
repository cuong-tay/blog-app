package com.cuongtay.blog_app.generator;

import com.cuongtay.blog_app.entity.User;
import com.cuongtay.blog_app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.hibernate.id.IdentifierGenerator;


public class UserIdGenerator implements IdentifierGenerator {

    @Override
    public Object generate(org.hibernate.engine.spi.SharedSessionContractImplementor session, Object object) {
        var user=(User)object;
        var role =user.getRole();
        var hql="select count(*) from User u where role=:role";
        long count = session.createSelectionQuery(hql,Long.class)
                .setParameter("role",role)
                .uniqueResult();
        return String.format("%c-%d",role.toString().charAt(0),count+1);
    }
}
