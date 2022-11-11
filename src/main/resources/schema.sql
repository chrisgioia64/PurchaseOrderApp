
CREATE TABLE po_purchase_order (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `approvedLimit` DOUBLE NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE po_part (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE po_line_item (
	`id` INT NOT NULL AUTO_INCREMENT,
    `quantity` INT NOT NULL,
    `part_id` INT NOT NULL,
    `po_id` INT NOT NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE po_line_item
	ADD FOREIGN KEY `p_id` (`part_id`) REFERENCES po_purchase_order(`id`) ON DELETE CASCADE;

ALTER TABLE po_line_item
	ADD FOREIGN KEY `purchase_order_id` (`po_id`) REFERENCES po_part(`id`) ON DELETE CASCADE;

