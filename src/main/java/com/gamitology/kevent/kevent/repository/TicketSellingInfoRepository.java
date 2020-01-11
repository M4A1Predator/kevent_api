package com.gamitology.kevent.kevent.repository;

import com.gamitology.kevent.kevent.entity.TicketSellingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketSellingInfoRepository extends JpaRepository<TicketSellingInfo, Integer> {

    List<TicketSellingInfo> removeByEventId(Integer eventId);

}
