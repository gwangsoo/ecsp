package com.example.xyz.repository;

import com.example.xyz.domain.entity.XyzDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Announcement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface XyzDetailRepository extends JpaRepository<XyzDetail, String>, JpaSpecificationExecutor<XyzDetail> {

    class Spec implements AbstractSpecification<XyzDetail> {

        private static Spec _instance = null;

        public static Spec instance() {
            if(_instance == null) {
                _instance = new Spec();
            }
            return _instance;
        }

        // 추가로 더 필요한 method 는 여기에...
    }

//    @Query("select sum(length(a.title)) from Announcement a")
//    Long getLength();
//
//    @Query("select length(a.title) from Announcement a")
//    Page<Long> getLength(Pageable pageable);
//
//    @Query("select sum(length(a.title)) from Announcement a")
//    Long getLength(Specification<Announcement> specification);
}
