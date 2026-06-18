package fr.diiage.org.indddid._6.elasticsearch;

import fr.diiage.org.indddid._6.elasticsearch.model.Utilisateur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
    extends ElasticsearchRepository<Utilisateur, String> {}
