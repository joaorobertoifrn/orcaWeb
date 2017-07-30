DROP DATABASE orcaweb;
CREATE DATABASE orcaweb;

USE orcaweb;


CREATE TABLE IF NOT EXISTS `orcaweb`.`Usuario` (
  `idUsuario` INT NOT NULL AUTO_INCREMENT,
  `usuario` VARCHAR(45) NULL,
  `senha` VARCHAR(45) NULL,
  PRIMARY KEY (`idUsuario`));

 
CREATE TABLE IF NOT EXISTS `orcaweb`.`Fase` (
  `idFase` INT NOT NULL AUTO_INCREMENT,
  `item` VARCHAR(10) NULL,
  `descricao` VARCHAR(45) NULL,
  PRIMARY KEY (`idFase`));

 
CREATE TABLE IF NOT EXISTS `orcaweb`.`Obra` (
  `idObra` INT NOT NULL AUTO_INCREMENT,
  `nomeObra` VARCHAR(45) NULL,
  `BDI` FLOAT NULL,
  PRIMARY KEY (`idObra`));

 
CREATE TABLE IF NOT EXISTS `orcaweb`.`Base` (
  `idBasePrecos` INT NOT NULL AUTO_INCREMENT,
  `mesAno` VARCHAR(6) NOT NULL,
  `url` VARCHAR(200) NULL,
  PRIMARY KEY (`idBasePrecos`, `mesAno`));
 
 
 CREATE TABLE IF NOT EXISTS `orcaweb`.`Composicao` (
  `idBase_FK` INT NULL,
  `classe` VARCHAR(50) NULL,
  `tipo` VARCHAR(1) NULL,
  `idComposicao` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(200) NULL,
  `unidade` VARCHAR(10) NULL,
  `valorUnitario` FLOAT NULL,
  PRIMARY KEY (`idComposicao`),
  INDEX `fk_BaseComposicao_BasePrecos1_idx` (`idBase_FK` ASC),
  CONSTRAINT `fk_BaseComposicao_BasePrecos1`
    FOREIGN KEY (`idBase_FK`)
    REFERENCES `orcaweb`.`Base` (`idBasePrecos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION); 
    
 
CREATE TABLE IF NOT EXISTS `orcaweb`.`ComposicaoItens` (
  `idComposicaoItens` INT NOT NULL AUTO_INCREMENT,
  `idComposicao_FK` INT NULL,
  `tipoItem` VARCHAR(1) NULL,
  `codigoItem` INT NULL,
  `descricao` VARCHAR(200) NULL,
  `coeficiente` FLOAT NULL,
  `unidade` VARCHAR(20) NULL,
  `precoUnitario` FLOAT NULL,
  `total` FLOAT NULL,
  PRIMARY KEY (`idComposicaoItens`),
  INDEX `fk_ComposicaoItens_Composicao1_idx` (`idComposicao_FK` ASC),
  CONSTRAINT `fk_ComposicaoItens_Composicao1`
    FOREIGN KEY (`idComposicao_FK`)
    REFERENCES `orcaweb`.`Composicao` (`idComposicao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION); 
    
 
CREATE TABLE IF NOT EXISTS `orcaweb`.`Orcamento` (
  `idOrcamento` INT NOT NULL AUTO_INCREMENT,
  `idObra_FK` INT NULL,
  `idBase_FK` INT NULL,
  `descricao` VARCHAR(45) NULL,
  `total` FLOAT NULL,
  PRIMARY KEY (`idOrcamento`),
  INDEX `fk_Orcamento_Base_idx` (`idBase_FK` ASC),
  INDEX `fk_Orcamento_Obra_idx` (`idObra_FK` ASC),
  CONSTRAINT `fk_Orcamento_Base1`
    FOREIGN KEY (`idBase_FK`)
    REFERENCES `orcaweb`.`Base` (`idBasePrecos`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Orcamento_Obra1`
    FOREIGN KEY (`idObra_FK`)
    REFERENCES `orcaweb`.`Obra` (`idObra`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);   
  
   
  CREATE TABLE IF NOT EXISTS `orcaweb`.`OrcamentoItens` (
  `idFase_FK` INT NULL,
  `idOrcamentoItens` INT NOT NULL AUTO_INCREMENT,
  `idOrcamento_FK` INT NULL,
  `tipoItem` VARCHAR(1) NULL,
  `codigoItem` INT NULL,
  `descricao` VARCHAR(100) NULL,
  `unidade` VARCHAR(10) NULL,
  `coeficiente` FLOAT NULL,
  `quantidade` FLOAT NULL,
  `precoUnitario` FLOAT NULL,
  `total` FLOAT NULL,
  PRIMARY KEY (`idOrcamentoItens`),
  INDEX `fk_OrcamentoItens_Fase1_idx` (`idFase_FK` ASC),
  INDEX `fk_OrcamentoItens_Orcamento1_idx` (`idOrcamento_FK` ASC),
  INDEX `fk_OrcamentoItens_CompoInsumos1_idx` (`codigoItem` ASC),
  CONSTRAINT `fk_OrcamentoItens_Fase1`
    FOREIGN KEY (`idFase_FK`)
    REFERENCES `orcaweb`.`Fase` (`idFase`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_OrcamentoItens_Orcamento1`
    FOREIGN KEY (`idOrcamento_FK`)
    REFERENCES `orcaweb`.`Orcamento` (`idOrcamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_OrcamentoItens_CompoInsumos1`
    FOREIGN KEY (`codigoItem`)
    REFERENCES `orcaweb`.`Composicao` (`idComposicao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
 
  
   
  
  
 
 