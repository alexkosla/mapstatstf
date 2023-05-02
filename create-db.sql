-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Users` (
  `Steam64ID` INT NOT NULL COMMENT '64-bit format of steam ID, used to identify users',
  `Steam3ID` VARCHAR(20) NOT NULL COMMENT 'Alternate format of Steam64ID useful to save to avoid needing to constantly calculate it',
  `Username` VARCHAR(20) NOT NULL COMMENT 'User\'s personally entered username',
  `PreferredClass` VARCHAR(20) NULL COMMENT 'User\'s favorite class',
  `IsAdmin` TINYINT NOT NULL COMMENT 'True if the user is an Admin\n',
  PRIMARY KEY (`Steam64ID`));


-- -----------------------------------------------------
-- Table `mydb`.`Maps`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Maps` (
  `MapID` VARCHAR(36) NOT NULL COMMENT 'ID of Map Object',
  `MapNameShort` VARCHAR(45) NOT NULL COMMENT 'Colloquial name of map',
  `ImgLocation` VARCHAR(100) NULL COMMENT 'location of map image in relation to base directory',
  `MapNameFull` VARCHAR(45) NOT NULL COMMENT 'full name of the map, ie \"pl_badwater_final1\"',
  PRIMARY KEY (`MapID`));


-- -----------------------------------------------------
-- Table `mydb`.`MasterStats`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`MasterStats` (
  `StatId` VARCHAR(45) NOT NULL,
  `LogID` INT NOT NULL COMMENT 'Unique ID used to find match\'s ID in logs.tf database',
  `Steam64ID` INT NOT NULL COMMENT 'Foreign key to user table',
  `Class` VARCHAR(45) NOT NULL COMMENT 'Class the user who created the entry was playing in the match',
  `MapID` VARCHAR(36) NOT NULL COMMENT 'Foreign key to the map the match was played on',
  `Kills` INT NOT NULL COMMENT '# of kills user got in the match',
  `Assists` INT NOT NULL COMMENT '# of assists the user got in the match',
  `Deaths` INT NOT NULL COMMENT '# of deaths the user got in the match',
  `Damage` INT NOT NULL COMMENT 'Amount of damage user did in the match',
  `Damage Taken` INT NOT NULL COMMENT 'Amount of damage user received in the match',
  `SecondsPlayed` INT NOT NULL COMMENT '# of seconds the user played in the match',
  PRIMARY KEY (`StatId`),
  INDEX `fk_MasterStats_Users_idx` (`Steam64ID` ASC) VISIBLE,
  INDEX `fk_MasterStats_Maps1_idx` (`MapID` ASC) VISIBLE,
  CONSTRAINT `fk_MasterStats_Users`
    FOREIGN KEY (`Steam64ID`)
    REFERENCES `mydb`.`Users` (`Steam64ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MasterStats_Maps1`
    FOREIGN KEY (`MapID`)
    REFERENCES `mydb`.`Maps` (`MapID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Data for table `mydb`.`Users`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`Users` (`Steam64ID`, `Steam3ID`, `Username`, `PreferredClass`, `IsAdmin`) VALUES (76561198034955422, '[U:1:74689694]', 'tua', 'Sniper', True);
INSERT INTO `mydb`.`Users` (`Steam64ID`, `Steam3ID`, `Username`, `PreferredClass`, `IsAdmin`) VALUES (76561198068104187, '[U:1:107838459]', 'Joey Lemons', 'Demo', False);
INSERT INTO `mydb`.`Users` (`Steam64ID`, `Steam3ID`, `Username`, `PreferredClass`, `IsAdmin`) VALUES (76561198028658527, '[U:1:68392799]', 'nucket', 'Scout', False);
INSERT INTO `mydb`.`Users` (`Steam64ID`, `Steam3ID`, `Username`, `PreferredClass`, `IsAdmin`) VALUES (76561198059227222, '[U:1:98961494]', 'nurkz', 'Heavy', False);
INSERT INTO `mydb`.`Users` (`Steam64ID`, `Steam3ID`, `Username`, `PreferredClass`, `IsAdmin`) VALUES (76561198123721061, '[U:1:163455333]', 'stardust', 'Spy', False);

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`Maps`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`Maps` (`MapID`, `MapNameShort`, `ImgLocation`, `MapNameFull`) VALUES ('0', 'Steel', 'src/main/img/cp_steel_f12-full.png', 'cp_steel_f12');
INSERT INTO `mydb`.`Maps` (`MapID`, `MapNameShort`, `ImgLocation`, `MapNameFull`) VALUES ('1', 'Ashville', 'src/main/img/koth_ashville_final-full.png', 'koth_ashville_final');
INSERT INTO `mydb`.`Maps` (`MapID`, `MapNameShort`, `ImgLocation`, `MapNameFull`) VALUES ('2', 'Product', 'src/main/img/koth_product_final.png', 'koth_product_rcx');
INSERT INTO `mydb`.`Maps` (`MapID`, `MapNameShort`, `ImgLocation`, `MapNameFull`) VALUES ('3', 'Proot', 'src/main/img/koth_proot_b5a.png', 'koth_proot_b5a');
INSERT INTO `mydb`.`Maps` (`MapID`, `MapNameShort`, `ImgLocation`, `MapNameFull`) VALUES ('4', 'Swiftwater', 'src/main/img/pl_swiftwater_final1-full.jpg', 'pl_swiftwater_final1');
INSERT INTO `mydb`.`Maps` (`MapID`, `MapNameShort`, `ImgLocation`, `MapNameFull`) VALUES ('5', 'Upward', 'src/main/img/pl_upward_f10.png', 'pl_upward_f10');
INSERT INTO `mydb`.`Maps` (`MapID`, `MapNameShort`, `ImgLocation`, `MapNameFull`) VALUES ('6', 'Vigil', 'src/main/img/pl_vigil_rc9.png', 'pl_vigil_rc9');

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`MasterStats`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`MasterStats` (`StatId`, `LogID`, `Steam64ID`, `Class`, `MapID`, `Kills`, `Assists`, `Deaths`, `Damage`, `Damage Taken`, `SecondsPlayed`) VALUES (DEFAULT, 2719534, 76561198034955422, 'Sniper', '2', 19, 1, 12, 7256, 3308, 854);
INSERT INTO `mydb`.`MasterStats` (`StatId`, `LogID`, `Steam64ID`, `Class`, `MapID`, `Kills`, `Assists`, `Deaths`, `Damage`, `Damage Taken`, `SecondsPlayed`) VALUES (DEFAULT, 2719534, 76561198068104187, 'Demo', '2', 13, 7, 13, 6589, 7421, 854);
INSERT INTO `mydb`.`MasterStats` (`StatId`, `LogID`, `Steam64ID`, `Class`, `MapID`, `Kills`, `Assists`, `Deaths`, `Damage`, `Damage Taken`, `SecondsPlayed`) VALUES (DEFAULT, 2719534, 76561198028658527, 'Scout', '2', 9, 3, 12, 3387, 3935, 854);
INSERT INTO `mydb`.`MasterStats` (`StatId`, `LogID`, `Steam64ID`, `Class`, `MapID`, `Kills`, `Assists`, `Deaths`, `Damage`, `Damage Taken`, `SecondsPlayed`) VALUES (DEFAULT, 2759736, 76561198123721061, 'Spy', '6', 3, 3, 9, 1238, 1627, 775);
INSERT INTO `mydb`.`MasterStats` (`StatId`, `LogID`, `Steam64ID`, `Class`, `MapID`, `Kills`, `Assists`, `Deaths`, `Damage`, `Damage Taken`, `SecondsPlayed`) VALUES (DEFAULT, 2759736, 76561198059227222, 'Heavy', '6', 6, 2, 7, 2097, 4394, 775);
INSERT INTO `mydb`.`MasterStats` (`StatId`, `LogID`, `Steam64ID`, `Class`, `MapID`, `Kills`, `Assists`, `Deaths`, `Damage`, `Damage Taken`, `SecondsPlayed`) VALUES (DEFAULT, 2759736, 76561198034955422, 'Sniper', '6', 9, 2, 5, 2632, 2741, 775);
INSERT INTO `mydb`.`MasterStats` (`StatId`, `LogID`, `Steam64ID`, `Class`, `MapID`, `Kills`, `Assists`, `Deaths`, `Damage`, `Damage Taken`, `SecondsPlayed`) VALUES (DEFAULT, 2759736, 76561198068104187, 'Demo', '6', 19, 1, 6, 7540, 4990, 775);

COMMIT;

