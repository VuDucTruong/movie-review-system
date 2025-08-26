-- Function to handle AFTER UPDATE on review
CREATE OR REPLACE FUNCTION update_review_statistic_on_update()
    RETURNS TRIGGER AS $$
BEGIN
    -- Only process when review is approved
    IF NEW.approved = true THEN
        -- If rating changed or approval just switched from false → true
        IF (OLD.rating IS DISTINCT FROM NEW.rating) OR (OLD.approved = false AND NEW.approved = true) THEN
            -- Decrement old rating count (only if old row was approved)
            IF OLD.approved = true THEN
                CASE OLD.rating
                    WHEN 1 THEN UPDATE review_statistic SET review1count = review1count - 1 WHERE movie_id = OLD.movie_id;
                    WHEN 2 THEN UPDATE review_statistic SET review2count = review2count - 1 WHERE movie_id = OLD.movie_id;
                    WHEN 3 THEN UPDATE review_statistic SET review3count = review3count - 1 WHERE movie_id = OLD.movie_id;
                    WHEN 4 THEN UPDATE review_statistic SET review4count = review4count - 1 WHERE movie_id = OLD.movie_id;
                    WHEN 5 THEN UPDATE review_statistic SET review5count = review5count - 1 WHERE movie_id = OLD.movie_id;
                    END CASE;
            END IF;

            -- Increment new rating count
            CASE NEW.rating
                WHEN 1 THEN UPDATE review_statistic SET review1count = review1count + 1 WHERE movie_id = NEW.movie_id;
                WHEN 2 THEN UPDATE review_statistic SET review2count = review2count + 1 WHERE movie_id = NEW.movie_id;
                WHEN 3 THEN UPDATE review_statistic SET review3count = review3count + 1 WHERE movie_id = NEW.movie_id;
                WHEN 4 THEN UPDATE review_statistic SET review4count = review4count + 1 WHERE movie_id = NEW.movie_id;
                WHEN 5 THEN UPDATE review_statistic SET review5count = review5count + 1 WHERE movie_id = NEW.movie_id;
                END CASE;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- Function to handle AFTER DELETE on review
CREATE OR REPLACE FUNCTION update_review_statistic_on_delete()
    RETURNS TRIGGER AS $$
BEGIN
    -- Only process if deleted row was approved
    IF OLD.approved = true THEN
        CASE OLD.rating
            WHEN 1 THEN UPDATE review_statistic SET review1count = review1count - 1 WHERE movie_id = OLD.movie_id;
            WHEN 2 THEN UPDATE review_statistic SET review2count = review2count - 1 WHERE movie_id = OLD.movie_id;
            WHEN 3 THEN UPDATE review_statistic SET review3count = review3count - 1 WHERE movie_id = OLD.movie_id;
            WHEN 4 THEN UPDATE review_statistic SET review4count = review4count - 1 WHERE movie_id = OLD.movie_id;
            WHEN 5 THEN UPDATE review_statistic SET review5count = review5count - 1 WHERE movie_id = OLD.movie_id;
            END CASE;
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;


-- Triggers
DROP TRIGGER IF EXISTS trg_update_review_statistic ON review;
CREATE TRIGGER trg_update_review_statistic
    AFTER UPDATE ON review
    FOR EACH ROW
EXECUTE FUNCTION update_review_statistic_on_update();

DROP TRIGGER IF EXISTS trg_delete_review_statistic ON review;
CREATE TRIGGER trg_delete_review_statistic
    AFTER DELETE ON review
    FOR EACH ROW
EXECUTE FUNCTION update_review_statistic_on_delete();

--- function update like review
CREATE OR REPLACE FUNCTION update_total_likes_on_create()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE review SET totalLikes = totalLikes + 1 WHERE id = NEW.reviewId;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_total_likes_on_delete()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE review SET totalLikes = totalLikes - 1 WHERE id = NEW.reviewId;
END;
$$ LANGUAGE plpgsql;

--- Triggers
DROP TRIGGER IF EXISTS trg_create_total_likes ON review_like;
CREATE TRIGGER trg_create_total_likes
    AFTER INSERT ON review_like
    FOR EACH ROW
EXECUTE FUNCTION update_total_likes_on_create();

DROP TRIGGER IF EXISTS trg_delete_total_likes ON review_like;
CREATE TRIGGER trg_delete_total_likes
    AFTER DELETE ON review_like
    FOR EACH ROW
EXECUTE FUNCTION update_total_likes_on_delete();