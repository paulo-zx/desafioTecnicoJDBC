CREATE TABLE Veiculo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(255) NOT NULL,
    fabricante VARCHAR(255) NOT NULL,
    ano INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    tipo ENUM('CARRO', 'MOTO') NOT NULL,
    quantidadePortas INT DEFAULT NULL,
    tipoCombustivel ENUM('gasolina', 'etanol', 'diesel', 'flex') DEFAULT NULL,
    cilindrada INT DEFAULT NULL,
    CONSTRAINT chk_carro CHECK (
        (tipo = 'CARRO' AND quantidadePortas IS NOT NULL AND tipoCombustivel IS NOT NULL AND cilindrada IS NULL) OR
        (tipo = 'MOTO' AND cilindrada IS NOT NULL AND quantidadePortas IS NULL AND tipoCombustivel IS NULL)
    )
);
