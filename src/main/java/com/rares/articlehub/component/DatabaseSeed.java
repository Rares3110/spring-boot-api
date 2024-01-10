package com.rares.articlehub.component;

import com.rares.articlehub.model.Article;
import com.rares.articlehub.repository.ArticleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DatabaseSeed implements CommandLineRunner {

    private final ArticleRepository articleRepository;

    public DatabaseSeed(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void run(String... args) {
        if (articleRepository.count() == 0) {
            Article hpArticle = articleRepository.save(new Article(
                "Harry Potter",
                "Volumele Harry Potter formează o serie foarte populară în întreaga lume; ele aparțin genului fantastic și au fost scrise de către autoarea britanică J. K. Rowling. Cărțile tratează o lume a vrăjitorilor, protagonist fiind un tânăr vrăjitor numit Harry Potter, alături de prietenii lui Ron Weasley și Hermione Granger. Povestea se desfășoară în cea mai mare parte la Hogwarts, Școala de Magie, Farmece și Vrăjitorii, o școală pentru tinerii vrăjitori și magicieni. Punctul central al poveștii îl reprezintă conflictul dintre Harry și întunecatul vrăjitor Lord Voldemort, care i-a ucis părinții în misiunea sa de a domina lumea vrăjitorilor.",
                new ArrayList<>()
            ));
        }
    }
}
