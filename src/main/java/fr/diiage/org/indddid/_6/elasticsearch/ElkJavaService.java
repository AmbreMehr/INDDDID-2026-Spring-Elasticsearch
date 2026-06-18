package fr.diiage.org.indddid._6.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._helpers.esql.jdbc.ResultSetEsqlAdapter;
import co.elastic.clients.elasticsearch._helpers.esql.objects.ObjectsEsqlAdapter;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import fr.diiage.org.indddid._6.elasticsearch.model.Utilisateur;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ElkJavaService {

    private ElasticsearchClient esClient1;
    private ElasticsearchClient esClient2;

    public ElkJavaService(
        @Qualifier("elkClient1") ElasticsearchClient esClient1,
        @Qualifier("elkClient2") ElasticsearchClient esClient2
    ) {
        this.esClient1 = esClient1;
        this.esClient2 = esClient2;
    }

    public void creationIndex() {
        try {
            esClient1.indices().create(c -> c.index("utilisateurs"));
            esClient2.indices().create(c -> c.index("utilisateurs"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ajoutUtilisateurElk1(Utilisateur utilisateur) {
        try {
            esClient1.index(i -> i.index("utilisateurs").document(utilisateur));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ajoutUtilisateurElk2(Utilisateur utilisateur) {
        try {
            esClient2.index(i -> i.index("utilisateurs").document(utilisateur));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Iterable<Utilisateur> rechercheUtilisateursOutlookDSL() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try {
            SearchResponse<Utilisateur> searchResponse = esClient1.search(
                s ->
                    s
                        .index("utilisateurs")
                        .query(q ->
                            q.queryString(t ->
                                t.query("*outlook*").analyzeWildcard(true)
                            )
                        ),
                Utilisateur.class
            );
            for (Hit<Utilisateur> utilisateur : searchResponse.hits().hits()) {
                utilisateurs.add(utilisateur.source());
            }

            searchResponse = esClient2.search(
                s ->
                    s
                        .index("utilisateurs")
                        .query(q ->
                            q.queryString(t ->
                                t.query("*outlook*").analyzeWildcard(true)
                            )
                        ),
                Utilisateur.class
            );
            for (Hit<Utilisateur> utilisateur : searchResponse.hits().hits()) {
                utilisateurs.add(utilisateur.source());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    public Iterable<Utilisateur> rechercheUtilisateursOutlookESQL()
        throws Exception {
        String query = """
            FROM utilisateurs
            | WHERE email LIKE "*outlook*"
            | KEEP nom, prenom, email
            """;
        List<Utilisateur> result = (List<Utilisateur>) esClient1
            .esql()
            .query(ObjectsEsqlAdapter.of(Utilisateur.class), query);
        List<Utilisateur> result2 = (List<Utilisateur>) esClient2
            .esql()
            .query(ObjectsEsqlAdapter.of(Utilisateur.class), query);
        result.addAll(result2);
        return result;
    }
}
