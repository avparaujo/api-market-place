package br.com.alura.marketplace.infra.repository;

import br.com.alura.marketplace.domain.entity.Produto;
import br.com.alura.marketplace.domain.repository.ProdutoRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public interface ProdutoRepositoryExt extends ProdutoRepository {

    Optional<Produto> findByNome(String nome);

}
