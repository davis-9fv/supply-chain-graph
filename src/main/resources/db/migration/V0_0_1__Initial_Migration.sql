---
CREATE TABLE  IF NOT EXISTS edge (
                      id INT NOT NULL,
                      child_id INT NOT NULL,
                      PRIMARY KEY (id, child_id)
);

CREATE INDEX id_idx ON edge using btree (id);
CREATE INDEX child_id_idx ON edge using btree (child_id);


INSERT INTO edge (id, child_id) VALUES (1, 2);
INSERT INTO edge (id, child_id) VALUES (1, 3);
INSERT INTO edge (id, child_id) VALUES (2, 4);
INSERT INTO edge (id, child_id) VALUES (2, 5);
INSERT INTO edge (id, child_id) VALUES (3, 6);

INSERT INTO edge (id, child_id) VALUES (100, 200);
INSERT INTO edge (id, child_id) VALUES (100, 300);
