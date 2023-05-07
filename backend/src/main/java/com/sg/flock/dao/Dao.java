package com.sg.flock.dao;

import com.sg.flock.dto.Reply;
import com.sg.flock.dto.Tweet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@Repository
public class Dao  {
    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String user = "root";
    private final String pass = "uuuu";
    private final String dbName = "mydb";
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    DataSource dataSource = DataSourceFactory.createDataSource();
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    public void createTables() {
        String createDbSql = "CREATE DATABASE IF NOT EXISTS " + dbName;
        jdbcTemplate.execute(createDbSql);
        jdbcTemplate.execute("USE " + dbName);
        String tweetTableSql = "CREATE TABLE IF NOT EXISTS `mydb`.`tweet` (\n"
                + "  `id` INT NOT NULL AUTO_INCREMENT,\n"
                + "  `user_name` VARCHAR(300) NOT NULL,\n"
                + "  `title` TEXT NULL,\n"
                + "  `post` TEXT NULL,\n"
                + "  `img` LONGTEXT NULL,\n"
                + "  `date` TEXT NULL,\n"
                + "  PRIMARY KEY (`id`))\n"
                + "ENGINE = InnoDB;";
        jdbcTemplate.execute(tweetTableSql);
        String replyTableSql = "CREATE TABLE IF NOT EXISTS `mydb`.`reply` (\n"
                + "  `id` INT NOT NULL AUTO_INCREMENT,\n"
                + "  `tweet_id` INT NOT NULL,\n"
                + "  `user_name` VARCHAR(300) NULL,\n"
                + "  `title` TEXT NULL,\n"
                + "  `post` TEXT NULL,\n"
                + "  `img` LONGTEXT NULL,\n"
                + "  `date` TEXT NULL,\n"
                + "  PRIMARY KEY (`id`),\n"
                + "  INDEX `key_idx` (`tweet_id` ASC),\n"
                + "  CONSTRAINT `key`\n"
                + "    FOREIGN KEY (`tweet_id`)\n"
                + "    REFERENCES `mydb`.`tweet` (`id`)\n"
                + "    ON DELETE CASCADE\n"
                + " ) ENGINE = InnoDB;";
        jdbcTemplate.execute(replyTableSql);
        System.out.println("Tables created successfully.");
    }
    public void insertTweet(Tweet tweet) {
        LocalDateTime dateTime = LocalDateTime.now();
        String dateTimeStr = dateTime.format(formatter);
        tweet.setDate(dateTimeStr);
        String insertTweetSql = "INSERT INTO `mydb`.`tweet` (`user_name`, `title`, `post`, `img`, `date`) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(insertTweetSql, tweet.getUser_name(), tweet.getTitle(), tweet.getPost(), tweet.getImage(), tweet.getDate());
    }
    public void insertReply(Reply reply) {
        LocalDateTime dateTime = LocalDateTime.now();
        String dateTimeStr = dateTime.format(formatter);
        reply.setDate(dateTimeStr);
        String sql = "INSERT INTO reply (tweet_id, user_name, title, post, img, date) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, reply.getTweet_id(), reply.getUserName(), reply.getTitle(), reply.getPost(), reply.getImg(), reply.getDate());
    }
    public void deleteTweetById(int id)  {
        Tweet tweet = getTweetById(id);

        String sql = "DELETE FROM `mydb`.`tweet` WHERE id = ?;";
        jdbcTemplate.update(sql, id);

    }
    public void editTweetById(int id, Tweet tweet) {
        String sql = "UPDATE `mydb`.`tweet` SET title = ?, post = ?, img = ? WHERE id = ?";
        jdbcTemplate.update(sql, tweet.getTitle(), tweet.getPost(), tweet.getImg(), id);
    }
    public void deleteReplyById(int tweetId, int replyId) {

        String sql = "DELETE FROM `mydb`.`reply` WHERE tweet_id = ? AND id = ?;";
        jdbcTemplate.update(sql, tweetId, replyId);
    }
    public void editReplyById(int tweetId, int replyId, Reply reply)  {
        String sql = "UPDATE `mydb`.`reply` SET title = ?, post = ?, img = ? WHERE tweet_id = ? AND id = ?;";
        jdbcTemplate.update(sql, reply.getTitle(), reply.getPost(), reply.getImg(), tweetId, replyId);
    }
    public Tweet getTweetById(int tweetId)  {
        String sql = "SELECT * FROM tweet WHERE id = ?";
        Tweet tweet = jdbcTemplate.queryForObject(sql, new Object[]{tweetId}, new RowMapper<Tweet>() {
            @Override
            public Tweet mapRow(ResultSet rs, int rowNum) throws SQLException {
                Tweet tweet = new Tweet();
                tweet.setId(rs.getInt("id"));
                tweet.setUser_name(rs.getString("user_name"));
                tweet.setTitle(rs.getString("title"));
                tweet.setPost(rs.getString("post"));
                tweet.setImage(rs.getString("img"));
                tweet.setDate(rs.getString("date"));
                return tweet;
            }
        });
        return tweet;
    }
    public List<Tweet> getTweetByUserName(String user_name) {
        String sql = "SELECT * FROM tweet WHERE user_name = ?";
        List<Tweet> tweets = jdbcTemplate.query(sql, new Object[]{user_name}, new RowMapper<Tweet>() {
            @Override
            public Tweet mapRow(ResultSet rs, int rowNum) throws SQLException {
                Tweet tweet = new Tweet();
                tweet.setId(rs.getInt("id"));
                tweet.setUser_name(rs.getString("user_name"));
                tweet.setTitle(rs.getString("title"));
                tweet.setPost(rs.getString("post"));
                tweet.setImage(rs.getString("img"));
                tweet.setDate(rs.getString("date"));
                return tweet;
            }
        });
        return tweets;
    }
    public List<Reply> getReplyByUserName(String user_name)  {
        String sql = "SELECT * FROM reply WHERE user_name = ?";
        List<Reply> replies = jdbcTemplate.query(sql, new Object[]{user_name}, new RowMapper<Reply>() {
            @Override
            public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
                Reply reply = new Reply();
                reply.setId(rs.getInt("id"));
                reply.setTweetId(rs.getInt("tweet_id"));
                reply.setUserName(rs.getString("user_name"));
                reply.setTitle(rs.getString("title"));
                reply.setPost(rs.getString("post"));
                reply.setImg(rs.getString("img"));
                reply.setDate(rs.getString("date"));
                return reply;
            }
        });
        return replies;
    }
    public List<Tweet> getAllTweets(){
        String sql = "SELECT * FROM tweet";
        List<Tweet> tweets = jdbcTemplate.query(sql, new RowMapper<Tweet>() {
            @Override
            public Tweet mapRow(ResultSet rs, int rowNum) throws SQLException {
                Tweet tweet = new Tweet();
                tweet.setId(rs.getInt("id"));
                tweet.setUser_name(rs.getString("user_name"));
                tweet.setTitle(rs.getString("title"));
                tweet.setPost(rs.getString("post"));
                tweet.setImage(rs.getString("img"));
                tweet.setDate(rs.getString("date"));
                return tweet;
            }
        });
        return tweets;
    }
    public List<Reply> getAllReplies()  {
        String sql = "SELECT * FROM reply";
        List<Reply> replies = jdbcTemplate.query(sql, new RowMapper<Reply>() {
            @Override
            public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
                Reply reply = new Reply();
                reply.setId(rs.getInt("id"));
                reply.setTweetId(rs.getInt("tweet_id"));
                reply.setUserName(rs.getString("user_name"));
                reply.setTitle(rs.getString("title"));
                reply.setPost(rs.getString("post"));
                reply.setImg(rs.getString("img"));
                reply.setDate(rs.getString("date"));
                return reply;
            }
        });
        return replies;
    }
    public List<Reply> getRepliesForTweetId(int tweetId)  {
        String sql = "SELECT * FROM reply WHERE tweet_id = ?";
        List<Reply> replies = jdbcTemplate.query(sql, new Object[]{tweetId}, new RowMapper<Reply>() {
            @Override
            public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
                Reply reply = new Reply();
                reply.setId(rs.getInt("id"));
                reply.setTweetId(rs.getInt("tweet_id"));
                reply.setUserName(rs.getString("user_name"));
                reply.setTitle(rs.getString("title"));
                reply.setPost(rs.getString("post"));
                reply.setImg(rs.getString("img"));
                reply.setDate(rs.getString("date"));
                return reply;
            }
        });
        return replies;
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
                    if (currentTweet != null) {
                        currentTweet.setReplies((LinkedList<Reply>) currentReplies);
                        tweets.add(currentTweet);
                    }
                    currentTweet = new Tweet();
                    currentTweet.setId(tweetId);
                    currentTweet.setUser_name(rs.getString("t.user_name"));
                    currentTweet.setTitle(rs.getString("t.title"));
                    currentTweet.setPost(rs.getString("t.post"));
                    currentTweet.setImage(rs.getString("t.img"));
                    currentTweet.setDate(rs.getString("t.date"));
                    currentReplies = new LinkedList<>();
                    currentTweetId = tweetId;
                }
                if (rs.getInt("r.id") != 0) {
                    Reply reply = new Reply();
                    reply.setId(rs.getInt("r.id"));
                    reply.setTweetId(rs.getInt("r.tweet_id"));
                    reply.setUserName(rs.getString("r.user_name"));
                    reply.setTitle(rs.getString("r.title"));
                    reply.setPost(rs.getString("r.post"));
                    reply.setImg(rs.getString("r.img"));
                    reply.setDate(rs.getString("r.date"));
                    currentReplies.add(reply);
                }
            }
            if (currentTweet != null) {
                currentTweet.setReplies((LinkedList<Reply>) currentReplies);
                tweets.add(currentTweet);
            }
        });
        return tweets;
    }
    public List<String> convertTweetsToStrings(List<Tweet> tweets)  {
        List<String> strings = new LinkedList<>();
        for (Tweet tweet : tweets) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("[%d] %s - %s\n", tweet.getId(), tweet.getUser_name(), tweet.getDate()));
            sb.append(String.format("%s\n", tweet.getTitle()));
            sb.append(String.format("%s\n", tweet.getPost()));
            sb.append(String.format("%s\n", tweet.getImage()));
            List<Reply> replies = tweet.getReplies();
            if (replies != null && !replies.isEmpty()) {
                sb.append(String.format("Replies:\n"));
                for (Reply reply : replies) {
                    sb.append(String.format("\t[%d] %s - %s\n", reply.getId(), reply.getUserName(), reply.getDate()));
                    sb.append(String.format("\t%s\n", reply.getTitle()));
                    sb.append(String.format("\t%s\n", reply.getPost()));
                    sb.append(String.format("\t%s\n", reply.getImg()));
                }
            }
            strings.add(sb.toString());
        }
        return strings;
    }
    public void clearReplyTable() {
        final String sql = "DELETE FROM reply where id > 0";
        jdbcTemplate.update(sql);
        final String resetAutoIncrementSql = "ALTER TABLE reply AUTO_INCREMENT = 1";
        jdbcTemplate.update(resetAutoIncrementSql);
    }
    public void clearTweetTable() {
        final String sql = "DELETE FROM tweet where id > 0";
        jdbcTemplate.update(sql);
        final String resetAutoIncrementSql = "ALTER TABLE tweet AUTO_INCREMENT = 1";
        jdbcTemplate.update(resetAutoIncrementSql);
    }
}
