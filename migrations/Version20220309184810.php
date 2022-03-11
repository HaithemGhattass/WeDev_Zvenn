<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220309184810 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE user ADD phonecode VARCHAR(255) NOT NULL, ADD is_verified_phone TINYINT(1) NOT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE commande CHANGE adresse_livraison adresse_livraison VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE mode_livraison mode_livraison VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE renseignement renseignement VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE messages CHANGE message message VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE produits CHANGE nom_produit nom_produit VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE description_produit description_produit LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE nomimage_produit nomimage_produit VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE profanities CHANGE word word VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE reclamation CHANGE titre titre VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE description description LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE nomimage nomimage VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE etat etat VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE traitepar traitepar VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE reply CHANGE titre titre VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE description description LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE reset_password_request CHANGE selector selector VARCHAR(20) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE hashed_token hashed_token VARCHAR(100) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE restaurant CHANGE nom_restaurant nom_restaurant VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE nom_image nom_image VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE categorie_restaurant categorie_restaurant VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE addresse addresse VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE cite cite VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE status status VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE description description LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE `user` DROP phonecode, DROP is_verified_phone, CHANGE email email VARCHAR(180) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE password password VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE nom nom VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE prenom prenom VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE nom_image nom_image VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE adresse adresse VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE pseudo pseudo VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE sexe sexe VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
    }
}
