-- liquibase formatted sql

DELETE FROM comments WHERE ad_id IN (SELECT pk FROM ads WHERE author_id IN (6));
DELETE FROM comments WHERE author_id IN (6);
DELETE FROM ads WHERE author_id IN (6);
DELETE FROM users WHERE id IN (3);
