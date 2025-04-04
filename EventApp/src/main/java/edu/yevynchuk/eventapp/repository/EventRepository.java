package edu.yevynchuk.eventapp.repository;

import edu.yevynchuk.eventapp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Modifying
    @Query(value = "DELETE FROM event_user WHERE event_id = :eventId", nativeQuery = true)
    void deleteEventUsersByEventId(@Param("eventId") Long eventId);
}
