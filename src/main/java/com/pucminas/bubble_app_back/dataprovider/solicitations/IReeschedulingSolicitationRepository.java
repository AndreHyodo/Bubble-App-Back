package com.pucminas.bubble_app_back.dataprovider.solicitations;

import com.pucminas.bubble_app_back.model.solicitations.ReeschedulingSolicitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReeschedulingSolicitationRepository extends JpaRepository<ReeschedulingSolicitation, Long> {

    List<ReeschedulingSolicitation> findByStudentId(Long studentId);

    List<ReeschedulingSolicitation> findByRebookingLesson_LessonPackage_TeacherId(Long teacherId);

}
