package ru.gb.javajun;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.transaction.internal.TransactionImpl;
import ru.gb.javajun.entity.Post;
import ru.gb.javajun.entity.PostComment;

/**
 * Используя hibernate, создать таблицы:
 * 1. Post (публикация) (id, title)
 * 2. PostComment (комментарий к публикации) (id, text, post_id)
 * <p>
 * Написать стандартные CRUD-методы: создание, загрузка, удаление.
 * <p>
 */


public class Main {
    public static void main(String[] args) {
        Post post1 = new Post();
        post1.setId(2L);
        post1.setTitle("!!!!");

        Post post2 = new Post();
        post2.setId(3L);
        post2.setTitle("Уехала");

        Post post3 = new Post();
        post3.setId(4L);
        post3.setTitle("Yes))))");

        PostComment postComment1 = new PostComment();
        postComment1.setId(2L);
        postComment1.setText("!!!!)");
        postComment1.setPost(post1);

        PostComment postComment2 = new PostComment();
        postComment2.setId(3L);
        postComment2.setText("???");
        postComment2.setPost(post2);

        PostComment postComment3 = new PostComment();
        postComment3.setId(4L);
        postComment3.setText("куда?");
        postComment3.setPost(post2);

        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            withSession(sessionFactory);
            withSessionCreatePost(sessionFactory,post1);
            withSessionCreatePost(sessionFactory,post2);
            withSessionCreatePost(sessionFactory,post3);

            withSessionCreatePostComment(sessionFactory, postComment1);
            withSessionCreatePostComment(sessionFactory, postComment2);
            withSessionCreatePostComment(sessionFactory, postComment3);

            withSessionUpdatePost(sessionFactory);
            withSessionDeletePostComment(sessionFactory);

        }
    }


    private static void withSession(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Post post = new Post();
            post.setId(1L);
            post.setTitle("все хорошо");

            PostComment postComment = new PostComment();
            postComment.setId(1L);
            postComment.setText("отлично)))");
            postComment.setPost(post);

            Transaction tx = session.beginTransaction();
            session.persist(post);
            session.persist(postComment);
            tx.commit();
        }
    }



    private static void withSessionCreatePostComment (SessionFactory sessionFactory, PostComment postComment){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(postComment);
            tx.commit();
        }
    }
    private static void withSessionCreatePost (SessionFactory sessionFactory, Post post){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(post);
            tx.commit();
        }
    }

    private static void withSessionUpdatePost (SessionFactory sessionFactory){
        try (Session session = sessionFactory.openSession()) {
            Post postUpdate = session.find(Post.class, 2L);
            postUpdate.setTitle("Не успела!!!");
            Transaction tx = session.beginTransaction();
            session.merge(postUpdate);
            tx.commit();
        }
    }

    private static void withSessionDeletePostComment (SessionFactory sessionFactory){
        try (Session session = sessionFactory.openSession()) {
            PostComment postDelete = session.find(PostComment.class, 1L);
            Transaction tx = session.beginTransaction();
            session.remove(postDelete);
            tx.commit();
        }
    }

}