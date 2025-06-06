package com.backend.backend.specifications;

import com.backend.backend.entities.User;
import com.backend.backend.entities.UserStatus;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> hasNameLike(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<User> hasPhoneLike(String phone) {
        return (root, query, cb) ->
                phone == null ? null : cb.like(root.get("phone"), "%" + phone + "%");
    }

    public static Specification<User> hasStatus(UserStatus status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }
}
