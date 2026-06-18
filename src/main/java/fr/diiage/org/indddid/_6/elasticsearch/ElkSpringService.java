package fr.diiage.org.indddid._6.elasticsearch;

import fr.diiage.org.indddid._6.elasticsearch.model.Utilisateur;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ElkSpringService {

    private Logger log = LogManager.getLogger(ElkSpringService.class);

    private final UserRepository userRepository;

    public ElkSpringService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void elkAjoutUtilisateur(Utilisateur utilisateur) {
        log.info(
            "[ELK SPRING] Ajout de l'utilisateur {} {}",
            utilisateur.getPrenom(),
            utilisateur.getNom()
        );
        userRepository.save(utilisateur);
    }

    public void elkSupprimerUtilisateur(String id) {
        Utilisateur utilisateur = userRepository.findById(id).orElse(null);
        if (utilisateur != null) {
            log.info(
                "[ELK SPRING] Suppression de l'utilisateur {} {}",
                utilisateur.getPrenom(),
                utilisateur.getNom()
            );
            userRepository.deleteById(id);
        } else {
            log.warn("[ELK SPRING] Utilisateur non trouvé avec l'id {}", id);
        }
    }

    public Iterable<Utilisateur> elkListerUtilisateurs() {
        log.info("[ELK SPRING] Listing des utilisateurs");
        return userRepository.findAll();
    }
}
