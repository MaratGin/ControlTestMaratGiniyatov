package itis.socialtest;


import itis.socialtest.entities.Author;
import itis.socialtest.entities.Post;
import javafx.geometry.Pos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/*
 * В папке resources находятся два .csv файла.
 * Один содержит данные о постах в соцсети в следующем формате: Id автора, число лайков, дата, текст
 * Второй содержит данные о пользователях  - id, никнейм и дату рождения
 *
 * Напишите код, который превратит содержимое файлов в обьекты в package "entities"
 * и осуществите над ними следующие опреации:
 *
 * 1. Выведите в консоль все посты в читабельном виде, с информацией об авторе.
 * 2. Выведите все посты за сегодняшнюю дату
 * 3. Выведите все посты автора с ником "varlamov"
 * 4. Проверьте, содержит ли текст хотя бы одного поста слово "Россия"
 * 5. Выведите никнейм самого популярного автора (определять по сумме лайков на всех постах)
 *
 * Для выполнения заданий 2-5 используйте методы класса AnalyticsServiceImpl (которые вам нужно реализовать).
 *
 * Требования к реализации: все методы в AnalyticsService должны быть реализованы с использованием StreamApi.
 * Использование обычных циклов и дополнительных переменных приведет к снижению баллов, но допустимо.
 * Парсинг файлов и реализация методов оцениваются ОТДЕЛЬНО
 *
 *
 * */

public class MainClass {

    private List<Post> allPosts;

    private AnalyticsService analyticsService = new AnalyticsServiceImpl();

    public static void main(String[] args) throws IOException, ParseException {
        new MainClass().run("/Users/user/IdeaProjects/socialnetworktest2/src/itis/socialtest/resources/PostDatabase.csv", "/Users/user/IdeaProjects/socialnetworktest2/src/itis/socialtest/resources/Authors.csv");

    }

    private void run(String postsSourcePath, String authorsSourcePath) throws IOException, ParseException {
        List<Author> authorslist= new ArrayList<Author>();
        List<String> fileLines = Files.readAllLines(Paths.get(authorsSourcePath));

        String[] splitetr = new String[fileLines.size()];
        Long k=(long)0;
        int f=1;

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmm");
        for (String line:fileLines) {
            splitetr = line.split(",");
           Long id= Long.parseLong(splitetr[0]);
           String nick=splitetr[1];
          String date= String.format(splitetr[2],format );


            Author author= new Author(id,nick,date);

            authorslist.add(author);
        }
// Работа с постмами---------------------------------------------------------
        List<Post> postslist= new ArrayList<Post>();
        List<String> fileLiness = Files.readAllLines(Paths.get(postsSourcePath));

        String[] splitetrr = new String[fileLiness.size()];
          String test="1234567890";
        SimpleDateFormat format1 = new SimpleDateFormat(" ddMMyyyy'T'HHmm");
        for (String line:fileLiness) {
            splitetrr = line.split(",");

            Long id= Long.parseLong(splitetrr[0]);
             String[] splitetrForLikes=splitetrr[1].split(" ");

            Long likes=Long.parseLong(splitetrForLikes[1]);
           // String date= String.format(splitetrr[2],format1 );
            String date=String.format(splitetrr[2], format1);
            String text=splitetrr[3];


            for (Author author:authorslist) {
                if (id==author.getId()){
                    Post post= new Post(date,text,likes,author);
                    postslist.add(post);
                }
            }

        }
        for (Post post:postslist) {
            System.out.println(post);
        }
      allPosts=postslist;



    }
}
