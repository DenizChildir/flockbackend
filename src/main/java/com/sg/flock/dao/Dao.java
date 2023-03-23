package com.sg.flock.dao;

import com.sg.flock.dto.Reply;
import com.sg.flock.dto.Tweet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


@Repository
public class Dao {

    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String user = "root";
    private final String pass = "uuuu";
    private final String dbName = "mydb";
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    DataSource dataSource = DataSourceFactory.createDataSource();
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);


    public void createTables(){
        String createDbSql = "CREATE DATABASE IF NOT EXISTS " + dbName;
        jdbcTemplate.execute(createDbSql);

        jdbcTemplate.execute("USE " + dbName);

        String roundTableSql = "CREATE TABLE IF NOT EXISTS `mydb`.`tweet` (\n"
                + "  `id` INT NOT NULL AUTO_INCREMENT,\n"
                + "  `user_name` VARCHAR(300) NOT NULL,\n"
                + "  `title` TEXT NULL,\n"
                + "  `post` TEXT NULL,\n"
                + "  `img` TEXT NULL,\n"
                + "  `date` TEXT NULL,\n"
                + "  PRIMARY KEY (`id`))\n"
                + "ENGINE = InnoDB;";
        jdbcTemplate.execute(roundTableSql);

        String gameTableSql = "CREATE TABLE IF NOT EXISTS `mydb`.`reply` (\n"
                + "  `id` INT NOT NULL AUTO_INCREMENT,\n"
                + "  `tweet_id` INT NOT NULL,\n"
                + "  `user_name` VARCHAR(300) NULL,\n"
                + "  `title` TEXT NULL,\n"
                + "  `post` TEXT NULL,\n"
                + "  `img` TEXT NULL,\n"
                + "  `date` TEXT NULL,\n"
                + "  PRIMARY KEY (`id`),\n"
                + "  INDEX `key_idx` (`tweet_id` ASC),\n"
                + "  CONSTRAINT `key`\n"
                + "    FOREIGN KEY (`tweet_id`)\n"
                + "    REFERENCES `mydb`.`tweet` (`id`)\n"
                + "    ON DELETE CASCADE\n"
                + " ) ENGINE = InnoDB;";
        jdbcTemplate.execute(gameTableSql);

        System.out.println("Tables created successfully.");
    }
    public List<Tweet> getAllTweets2() {
        String tweetsSql = "SELECT * FROM tweet";
        String repliesSql = "SELECT * FROM reply WHERE tweet_id = ?";

        List<Tweet> tweets = jdbcTemplate.query(tweetsSql, new RowMapper<Tweet>() {
            @Override
            public Tweet mapRow(ResultSet rs, int rowNum) throws SQLException {
                int id = rs.getInt("id");
                String userName = rs.getString("user_name");
                String title = rs.getString("title");
                String post = rs.getString("post");
                String image = rs.getString("img");
                String date = rs.getString("date");

                List<Reply> replies = jdbcTemplate.query(repliesSql, new Object[]{id}, new RowMapper<Reply>() {
                    @Override
                    public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Reply reply = new Reply();
                        reply.setId(rs.getInt("id"));
                        reply.setTweetId(rs.getInt("tweet_id"));
                        reply.setUserName(rs.getString("user_name"));
                        reply.setTitle(rs.getString("title"));
                        reply.setPostText(rs.getString("post"));
                        reply.setImage(rs.getString("img"));
                        reply.setDate(rs.getString("date"));
                        return reply;
                    }
                });

                return new Tweet(id, userName, title, post, image, date, new LinkedList<>(replies));
            }
        });

        return tweets;
    }


    public List<Tweet> getTweetsWithReplies() {
        String sql = "SELECT * FROM tweet t LEFT JOIN reply r ON t.id = r.tweet_id ORDER BY t.id, r.id";
        List<Tweet> tweets = new LinkedList<>();

        jdbcTemplate.query(sql, (ResultSet rs) -> {
            int currentTweetId = 0;
            Tweet currentTweet = null;
            List<Reply> currentReplies = null;

            while (rs.next()) {
                int tweetId = rs.getInt("t.id");
                if (tweetId != currentTweetId) {
                    // We've moved on to a new tweet, so add the previous tweet to the list
                    if (currentTweet != null) {
                        currentTweet.setReplies((LinkedList<Reply>) currentReplies);
                        tweets.add(currentTweet);
                    }
                    // Create a new tweet object for the current row
                    currentTweet = new Tweet();
                    currentTweet.setId(tweetId);
                    currentTweet.setUserName(rs.getString("t.user_name"));
                    currentTweet.setTitle(rs.getString("t.title"));
                    currentTweet.setPost(rs.getString("t.post"));
                    currentTweet.setImage(rs.getString("t.img"));
                    currentTweet.setDate(rs.getString("t.date"));

                    // Create a new list to hold the replies for this tweet
                    currentReplies = new LinkedList<>();
                    currentTweetId = tweetId;
                }
                // If there is a reply for this row, add it to the current list of replies
                if (rs.getInt("r.id") != 0) {
                    Reply reply = new Reply();
                    reply.setId(rs.getInt("r.id"));
                    reply.setTweetId(rs.getInt("r.tweet_id"));
                    reply.setUserName(rs.getString("r.user_name"));
                    reply.setTitle(rs.getString("r.title"));
                    reply.setPostText(rs.getString("r.post"));
                    reply.setImage(rs.getString("r.img"));
                    reply.setDate(rs.getString("r.date"));
                    currentReplies.add(reply);
                }
            }
            // Add the last tweet to the list
            if (currentTweet != null) {
                currentTweet.setReplies((LinkedList<Reply>) currentReplies);
                tweets.add(currentTweet);
            }
        });
        return tweets;
    }


    public void insertTweet(Tweet tweet) {
        String insertTweetSql = "INSERT INTO `mydb`.`tweet` (`user_name`, `title`, `post`, `img`, `date`) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertTweetSql, tweet.getUserName(), tweet.getTitle(), tweet.getPost(), tweet.getImage(), tweet.getDate());
    }


    public void insertReply(Reply reply){
        String sql = "INSERT INTO reply (tweet_id, user_name, title, post, img, date) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, reply.getPostId(), reply.getUserName(), reply.getTitle(), reply.getPostText(), reply.getImage(), reply.getDate());
    }
    /*
        public void insertReply(int tweetId, String userName, String title, String post, String img, String date) {
        String sql = "INSERT INTO reply (tweet_id, user_name, title, post, img, date) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, tweetId, userName, title, post, img, date);
    }
    */
    

    public Tweet getTweetById(int tweetId) {
        String sql = "SELECT * FROM tweet WHERE id = ?";
        Tweet tweet = jdbcTemplate.queryForObject(sql, new Object[]{tweetId}, new RowMapper<Tweet>(){
            @Override
            public Tweet mapRow(ResultSet rs, int rowNum) throws SQLException {
                Tweet tweet = new Tweet();
                tweet.setId(rs.getInt("id"));
                tweet.setUserName(rs.getString("user_name"));
                tweet.setTitle(rs.getString("title"));
                tweet.setPost(rs.getString("post"));
                tweet.setImage(rs.getString("img"));
                tweet.setDate(rs.getString("date"));
                return tweet;
            }
        });
        return tweet;
    }
    

    public List<Tweet> getAllTweets(){
        String sql = "SELECT * FROM tweet";
        List<Tweet> tweets = jdbcTemplate.query(sql, new RowMapper<Tweet>() {
            @Override
            public Tweet mapRow(ResultSet rs, int rowNum) throws SQLException {
                Tweet tweet = new Tweet();
                tweet.setId(rs.getInt("id"));
                tweet.setUserName(rs.getString("user_name"));
                tweet.setTitle(rs.getString("title"));
                tweet.setPost(rs.getString("post"));
                tweet.setImage(rs.getString("img"));
                tweet.setDate(rs.getString("date"));
                return tweet;
            }
        });
        return tweets;
    }


    public List<Reply> getRepliesForTweetId(int tweetId){
        String sql = "SELECT * FROM reply WHERE tweet_id = ?";
        List<Reply> replies = jdbcTemplate.query(sql, new Object[]{tweetId}, new RowMapper<Reply>() {
            @Override
            public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
                Reply reply = new Reply();
                reply.setId(rs.getInt("id"));
                reply.setTweetId(rs.getInt("tweet_id"));
                reply.setUserName(rs.getString("user_name"));
                reply.setTitle(rs.getString("title"));
                reply.setPostText(rs.getString("post"));
                reply.setImage(rs.getString("img"));
                reply.setDate(rs.getString("date"));
                return reply;
            }
        });
        return replies;
    }

    public String convertTweetsToStrings(List<Tweet> tweets) {
        List<String> strings = new LinkedList<>();

        for (Tweet tweet : tweets) {
            StringBuilder sb = new StringBuilder();

            // Add the tweet information to the string
            sb.append(String.format("[%d] %s - %s\n", tweet.getId(), tweet.getUserName(), tweet.getDate()));
            sb.append(String.format("%s\n", tweet.getTitle()));
            sb.append(String.format("%s\n", tweet.getPost()));
            sb.append(String.format("%s\n", tweet.getImage()));

            // Add the replies to the string
            List<Reply> replies = tweet.getReplies();
            if (replies != null && !replies.isEmpty()) {
                sb.append(String.format("Replies:\n"));
                for (Reply reply : replies) {
                    sb.append(String.format("\t[%d] %s - %s\n", reply.getId(), reply.getUserName(), reply.getDate()));
                    sb.append(String.format("\t%s\n", reply.getTitle()));
                    sb.append(String.format("\t%s\n", reply.getPostText()));
                    sb.append(String.format("\t%s\n", reply.getImage()));
                }
            }
            // Add the complete tweet (with replies) to the list of strings
            strings.add(sb.toString());
        }

        return strings.toString();
    }

}