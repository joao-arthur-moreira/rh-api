DROP TABLE IF EXISTS pessoa;
DROP TABLE IF EXISTS setor;
 
CREATE TABLE setor (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  nome VARCHAR(50) NOT NULL
);

CREATE TABLE pessoa (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  nome VARCHAR(80) NOT NULL,
  descricao_cargo varchar(200),
  id_setor int
);
 
INSERT INTO setor (nome) VALUES
  ('TI');
  
INSERT INTO setor (nome) VALUES
  ('Comercial');
  
INSERT INTO setor (nome) VALUES
  ('RH');    
  
INSERT INTO pessoa (nome, id_setor) VALUES
  ('João Arthur', 1); 
  
INSERT INTO pessoa (nome, id_setor) VALUES
  ('João Levy', 1);  
  
INSERT INTO pessoa (nome, id_setor) VALUES
  ('Maria Sophia', 2);  