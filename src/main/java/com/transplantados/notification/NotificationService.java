package com.transplantados.notification;

import com.transplantados.patient.Patient;
import com.transplantados.professional.Professional;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {

        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        val notifications = notificationRepository.findAll();

        if (authenticatedUser instanceof Patient patient) {
            return notifications.parallelStream()
                    .filter(notification -> !notification.isPatientMark() && notification.getPatientId().equals(patient.getId()))
                    .toList();
        }

        if (authenticatedUser instanceof Professional professional) {
            return notifications.parallelStream()
                    .filter(notification -> !notification.getProfessionals().contains(professional.getId()) && notification.isToAllProfessionals())
                    .toList();
        }

        throw new IllegalStateException("User type not recognized: " + authenticatedUser.getClass().getName());
    }

    public void markNotificationAsRead(UUID notificationId) {
        val notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found with ID: " + notificationId));

        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authenticatedUser instanceof Patient) {
            notification.setPatientMark(true);
            notificationRepository.save(notification);
        }

        if (authenticatedUser instanceof Professional professional) {
            if (!notification.getProfessionals().contains(professional.getId())) {
                notification.getProfessionals().add(professional.getId());
            }
            notificationRepository.save(notification);
        }
    }

}
