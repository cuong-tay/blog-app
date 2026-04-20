
-- Insert data into posts table SECOND (independent)
INSERT INTO posts (id, title, description, content, create_at, update_at) VALUES
(1, 'Spring Boot Tutorial', 'Learn the basics of Spring Boot', 'This is a comprehensive guide to getting started with Spring Boot framework...', NOW(), NOW()),
(2, 'Java Best Practices', 'Tips for writing clean Java code', 'In this post, we will discuss the best practices for writing maintainable Java code...', NOW(), NOW()),
(3, 'Database Design', 'How to design efficient databases', 'A guide to designing scalable and efficient database schemas for your applications...', NOW(), NOW());

-- Insert data into comments table THIRD (depends on posts - has foreign key post_id)
INSERT INTO comments (id, name, email, body, post_id, created_at, updated_at) VALUES
(1, 'Alice Brown', 'alice@example.com', 'Great tutorial! Very helpful.', 1, NOW(), NOW()),
(2, 'Bob Wilson', 'bob@example.com', 'Thanks for sharing this knowledge!', 1, NOW(), NOW()),
(3, 'Carol Davis', 'carol@example.com', 'Excellent tips, will apply them in my project.', 2, NOW(), NOW());

