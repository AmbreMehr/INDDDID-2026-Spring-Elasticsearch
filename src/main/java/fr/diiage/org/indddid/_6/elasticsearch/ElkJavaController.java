package fr.diiage.org.indddid._6.elasticsearch;

import fr.diiage.org.indddid._6.elasticsearch.model.Utilisateur;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/java")
public class ElkJavaController {

    private final ElkJavaService elkService;

    public ElkJavaController(ElkJavaService elkService) {
        this.elkService = elkService;
    }

    @GetMapping("utilisateurs/creationIndex")
    public void creationIndex() {
        elkService.creationIndex();
    }

    @GetMapping("utilisateurs/getOutlookUserDSL")
    public Iterable<Utilisateur> getOutlookUserDSL() {
        return elkService.rechercheUtilisateursOutlookDSL();
    }

    @GetMapping("utilisateurs/getOutlookUserESQL")
    public Iterable<Utilisateur> getOutlookUserESQL() throws Exception {
        return elkService.rechercheUtilisateursOutlookESQL();
    }

    @PostMapping("utilisateurs/elk1")
    public void ajoutUtilisateurElk1(@RequestBody Utilisateur utilisateur) {
        elkService.ajoutUtilisateurElk1(utilisateur);
    }

    @PostMapping("utilisateurs/elk2")
    public void ajoutUtilisateurElk2(@RequestBody Utilisateur utilisateur) {
        elkService.ajoutUtilisateurElk2(utilisateur);
    }
}
