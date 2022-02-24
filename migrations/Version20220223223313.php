<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220223223313 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE user ADD nom VARCHAR(255) NOT NULL, ADD prenom VARCHAR(255) NOT NULL, ADD date_creation DATETIME NOT NULL, ADD image LONGBLOB NOT NULL, ADD nom_image VARCHAR(255) NOT NULL, ADD adresse VARCHAR(255) NOT NULL, ADD num_tel INT NOT NULL, ADD pseudo VARCHAR(255) NOT NULL, ADD date_naissance DATE NOT NULL, ADD sexe VARCHAR(255) NOT NULL, ADD etat INT NOT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE produits CHANGE nom_produit nom_produit VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE description_produit description_produit LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE nomimage_produit nomimage_produit VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE restaurant CHANGE nom_restaurant nom_restaurant VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE nom_image nom_image VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE categorie_restaurant categorie_restaurant VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE addresse addresse VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE cite cite VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE status status VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE description description LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE `user` DROP nom, DROP prenom, DROP date_creation, DROP image, DROP nom_image, DROP adresse, DROP num_tel, DROP pseudo, DROP date_naissance, DROP sexe, DROP etat, CHANGE email email VARCHAR(180) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE password password VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
    }
}
