package com.rares.articlehub.seed;

import com.rares.articlehub.model.*;
import com.rares.articlehub.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final ExternalResourceRepository externalResourceRepository;
    private final CommentRepository commentRepository;

    public DatabaseSeeder(UserRepository userRepository,
                          ArticleRepository articleRepository,
                          CategoryRepository categoryRepository,
                          ExternalResourceRepository externalResourceRepository,
                          CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.externalResourceRepository = externalResourceRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) {
        if(userRepository.count() != 0
                || articleRepository.count() != 0
                || categoryRepository.count() != 0
                || externalResourceRepository.count() != 0
                || commentRepository.count() != 0) {
            return;
        }

        List<User> userList = List.of(
                new User(
                        "Rares",
                        "rares@yahoo.com",
                        "rares1234!@",
                        UserRole.ADMIN
                ),
                new User(
                        "Radu",
                        "radu@yahoo.com",
                        "radu1234!@",
                        UserRole.USER
                )
        );

        List<Article> articleList = List.of(
                new Article(
                        "Harry Potter",
                        "Volumele Harry Potter formează o serie foarte populară în întreaga lume; ele aparțin genului fantastic și au fost scrise de către autoarea britanică J. K. Rowling. Cărțile tratează o lume a vrăjitorilor, protagonist fiind un tânăr vrăjitor numit Harry Potter, alături de prietenii lui Ron Weasley și Hermione Granger. Povestea se desfășoară în cea mai mare parte la Hogwarts, Școala de Magie, Farmece și Vrăjitorii, o școală pentru tinerii vrăjitori și magicieni. Punctul central al poveștii îl reprezintă conflictul dintre Harry și întunecatul vrăjitor Lord Voldemort, care i-a ucis părinții în misiunea sa de a domina lumea vrăjitorilor.",
                        userList.get(0)
                ),
                new Article(
                        "Războiul stelelor - Episodul I: Amenințarea fantomei",
                        "Războiul stelelor - Episodul I: Amenințarea fantomă (titlu original în limba engleză: Star Wars Episode I: The Phantom Menace) este un film epic american, o epopee spațială din 1999. Filmul este scris și regizat de George Lucas. Este al patrulea film care a fost lansat din seria de filme Războiul stelelor și primul film din punct de vedere al cronologiei întâmplărilor prezentate în serie.",
                        userList.get(0)
                ),
                new Article(
                        "Lord of the Rings",
                        "The Lord of the Rings is an epic[1] high fantasy novel[a] by the English author and scholar J. R. R. Tolkien. Set in Middle-earth, the story began as a sequel to Tolkien's 1937 children's book The Hobbit, but eventually developed into a much larger work. Written in stages between 1937 and 1949, The Lord of the Rings is one of the best-selling books ever written, with over 150 million copies sold.",
                        userList.get(1)
                )
        );

        List<Category> categoryList = List.of(
                new Category(
                        "Fantasy",
                        CategoryState.ACCEPTED,
                        articleList
                ),
                new Category(
                        "Harry Potter",
                        CategoryState.ACCEPTED,
                        List.of(articleList.get(0))
                ),
                new Category(
                        "Harry Skywalker",
                        CategoryState.REJECTED,
                        List.of(articleList.get(0))
                ),
                new Category(
                        "Science Fiction",
                        CategoryState.ACCEPTED,
                        List.of(articleList.get(1))
                ),
                new Category(
                        "Hobbits",
                        CategoryState.ACCEPTED,
                        List.of(articleList.get(2))
                )
        );

        List<ExternalResource> externalResourceList = List.of(
                new ExternalResource(
                    1,
                    "http://localhost:8080/random1",
                    articleList.get(0)
                ),
                new ExternalResource(
                        2,
                        "http://localhost:8080/random2",
                        articleList.get(0)
                ),
                new ExternalResource(
                        3,
                        "http://localhost:8080/random3",
                        articleList.get(0)
                ),
                new ExternalResource(
                        1,
                        "http://localhost:8080/random4",
                        articleList.get(1)
                ),
                new ExternalResource(
                        2,
                        "http://localhost:8080/random5",
                        articleList.get(1)
                ),
                new ExternalResource(
                        1,
                        "http://localhost:8080/random6",
                        articleList.get(2)
                ),
                new ExternalResource(
                        2,
                        "http://localhost:8080/random7",
                        articleList.get(2)
                ),
                new ExternalResource(
                        3,
                        "http://localhost:8080/random8",
                        articleList.get(2)
                ),
                new ExternalResource(
                        4,
                        "http://localhost:8080/random9",
                        articleList.get(2)
                )
        );

        List<Comment> commentToArticleList = List.of(
                new Comment(
                        "comment to article 1",
                        CommentVisibility.VISIBLE,
                        userList.get(1),
                        articleList.get(0),
                        null
                ),
                new Comment(
                        "comment to article 2",
                        CommentVisibility.VISIBLE,
                        userList.get(1),
                        articleList.get(0),
                        null
                ),
                new Comment(
                        "comment to article 3",
                        CommentVisibility.VISIBLE,
                        userList.get(1),
                        articleList.get(1),
                        null
                ),
                new Comment(
                        "comment to article 4",
                        CommentVisibility.VISIBLE,
                        userList.get(0),
                        articleList.get(1),
                        null
                ),
                new Comment(
                        "comment to article 5",
                        CommentVisibility.VISIBLE,
                        userList.get(1),
                        articleList.get(1),
                        null
                ),
                new Comment(
                        "comment to article 6",
                        CommentVisibility.VISIBLE,
                        userList.get(0),
                        articleList.get(2),
                        null
                ),
                new Comment(
                        "comment to article 7",
                        CommentVisibility.VISIBLE,
                        userList.get(1),
                        articleList.get(2),
                        null
                )
        );

        List<Comment> commentToCommentList = List.of(
                new Comment(
                        "comment to comment 1",
                        CommentVisibility.VISIBLE,
                        userList.get(0),
                        null,
                        commentToArticleList.get(0)
                ),
                new Comment(
                        "comment to comment 2",
                        CommentVisibility.VISIBLE,
                        userList.get(0),
                        null,
                        commentToArticleList.get(0)
                ),
                new Comment(
                        "comment to comment 3",
                        CommentVisibility.VISIBLE,
                        userList.get(0),
                        null,
                        commentToArticleList.get(0)
                ),
                new Comment(
                        "comment to comment 4",
                        CommentVisibility.VISIBLE,
                        userList.get(1),
                        null,
                        commentToArticleList.get(2)
                ),
                new Comment(
                        "comment to comment 5",
                        CommentVisibility.VISIBLE,
                        userList.get(0),
                        null,
                        commentToArticleList.get(3)
                ),
                new Comment(
                        "comment to comment 6",
                        CommentVisibility.VISIBLE,
                        userList.get(1),
                        null,
                        commentToArticleList.get(3)
                )
        );

        userRepository.saveAll(userList);
        articleRepository.saveAll(articleList);
        categoryRepository.saveAll(categoryList);
        externalResourceRepository.saveAll(externalResourceList);
        commentRepository.saveAll(commentToArticleList);
        commentRepository.saveAll(commentToCommentList);
    }
}
