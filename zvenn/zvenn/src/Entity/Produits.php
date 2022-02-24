<?php

namespace App\Entity;

use App\Repository\ProduitsRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=ProduitsRepository::class)
 */
class Produits
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */

    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $nom_produit;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $descriptionp;

    /**
     * @ORM\Column(type="blob")
     */
    private $image_produit;

    private $rawPhoto;

    public function displayPhoto()
    {
        if(null === $this->rawPhoto) {
            $this->rawPhoto = "data:image/png;base64," . base64_encode(stream_get_contents($this->getimageproduit()));
        }

        return $this->rawPhoto;
    }

    /**
     * @ORM\Column(type="float")
     */
    private $prix_produit;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $type_produit;



    public function getId(): ?int
    {
        return $this->id;
    }


    public function getnomproduit(): ?string
    {
        return $this->nom_produit;
    }

    public function setNomProduit(string $nom_produit): self
    {
        $this->nom_produit = $nom_produit;

        return $this;
    }

    public function getdescriptionp(): ?string
    {
        return $this->descriptionp;
    }

    public function setDescriptionp(string $descriptionp): self
    {
        $this->descriptionp = $descriptionp;

        return $this;
    }

    public function getimageproduit()
    {
        return $this->image_produit;
    }

    public function setImageProduit($image_produit): self
    {
        $this->image_produit = $image_produit;

        return $this;
    }

    public function getprixproduit(): ?float
    {
        return $this->prix_produit;
    }

    public function setPrixProduit(float $prix_produit): self
    {
        $this->prix_produit = $prix_produit;

        return $this;
    }

    public function getTypeProduit(): ?string
    {
        return $this->type_produit;
    }

    public function setTypeProduit(string $type_produit): self
    {
        $this->type_produit = $type_produit;

        return $this;
    }
}
