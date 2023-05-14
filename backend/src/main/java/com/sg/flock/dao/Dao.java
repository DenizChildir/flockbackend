package com.sg.flock.dao;

import com.sg.flock.dto.Reply;
import com.sg.flock.dto.Tweet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Component
@Repository
public class Dao  {
    private final String JDBC_DRIVER = "org.h2.Driver";
    private final String dbName = "mydb";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private final String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private final String user = "sa";
    private final String pass = "";
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public Dao() {
        this.dataSource = createDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(JDBC_DRIVER);
        dataSource.setUrl("jdbc:h2:mem:" + dbName + ";DB_CLOSE_DELAY=-1");
        dataSource.setUsername(user);
        dataSource.setPassword(pass);
        return dataSource;
    }

    public void createTables() {
        String tweetTableSql = "CREATE TABLE IF NOT EXISTS `tweet` (\n"
                + "  `id` INT AUTO_INCREMENT PRIMARY KEY,\n"
                + "  `user_name` VARCHAR(300) NOT NULL,\n"
                + "  `title` TEXT NULL,\n"
                + "  `post` TEXT NULL,\n"
                + "  `img` LONGTEXT NULL,\n"
                + "  `date` TIMESTAMP NULL\n"
                + ");";
        jdbcTemplate.execute(tweetTableSql);
        String replyTableSql = "CREATE TABLE IF NOT EXISTS `reply` (\n"
                + "  `id` INT AUTO_INCREMENT PRIMARY KEY,\n"
                + "  `tweet_id` INT NOT NULL,\n"
                + "  `user_name` VARCHAR(300) NULL,\n"
                + "  `title` TEXT NULL,\n"
                + "  `post` TEXT NULL,\n"
                + "  `img` LONGTEXT NULL,\n"
                + "  `date` TIMESTAMP NULL,\n"
                + "  FOREIGN KEY (`tweet_id`) REFERENCES `tweet` (`id`)\n"
                + ");";
        jdbcTemplate.execute(replyTableSql);
        System.out.println("Tables created successfully.");
    }
    public void insertTweet(Tweet tweet) {
        String insertTweetSql = "INSERT INTO `tweet` (`user_name`, `title`, `post`, `img`, `date`) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertTweetSql, tweet.getUser_name(), tweet.getTitle(), tweet.getPost(), tweet.getImage(), Timestamp.valueOf(LocalDateTime.now()));
    }
    public void insertReply(Reply reply) {
        String sql = "INSERT INTO reply (tweet_id, user_name, title, post, img, date) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, reply.getTweet_id(), reply.getUserName(), reply.getTitle(), reply.getPost(), reply.getImg(), Timestamp.valueOf(LocalDateTime.now()));
    }
    public void deleteTweetById(int id)  {
        String sql = "DELETE FROM `tweet` WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }
    public void editTweetById(int id, Tweet tweet) {
        String sql = "UPDATE `tweet` SET title = ?, post = ?, img = ? WHERE id = ?";
        jdbcTemplate.update(sql, tweet.getTitle(), tweet.getPost(), tweet.getImg(), id);
    }
    public void deleteReplyById(int tweetId, int replyId) {
        String sql = "DELETE FROM `reply` WHERE tweet_id = ? AND id = ?;";
        jdbcTemplate.update(sql, tweetId, replyId);
    }
    public void editReplyById(int tweetId, int replyId, Reply reply)  {
        String sql = "UPDATE `reply` SET title = ?, post = ?, img = ? WHERE tweet_id = ? AND id = ?;";
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
                tweet.setDate(rs.getTimestamp("date").toLocalDateTime().toString());
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
                tweet.setDate(rs.getTimestamp("date").toLocalDateTime().toString());
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
                reply.setDate(rs.getTimestamp("date").toLocalDateTime().toString());
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
                tweet.setDate(rs.getTimestamp("date").toLocalDateTime().toString());
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
                reply.setDate(rs.getTimestamp("date").toLocalDateTime().toString());
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
                reply.setDate(rs.getTimestamp("date").toLocalDateTime().toString());
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
                    currentTweet.setDate(rs.getTimestamp("t.date").toLocalDateTime().toString());
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
                    reply.setDate(rs.getTimestamp("r.date").toLocalDateTime().toString());
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
