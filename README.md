# Hotel Manager

## Contexte du projet

Projet de 2 étudiants réalisé dans le cadre du cours « Programmation objet avancée » - 8INF957 - UQAC Hiver 2018

Membres du groupe :

  * Loïc Bothorel : BOTL27029508
  * Rénald Morice : MORR14029503
  
Le mini-srs est présent à la racine de ce projet.

## Qu'est-ce « HotelManager » ?

Hotel Manager est une application Java permettant la gestion des réservations d'un hôtel. Elle permet de gérer :

  * les chambres
  * les réservations
  * les clients
  * les employés
  * le compte de l'employé connecté
  
## Aperçu : page des réservations

La capture d'écran ci-dessous correspond à l'écran permettant de visualiser/filtrer les réservations :

![Reservations](/img-readme/reservation.PNG)

La capture d'écran correspond à l'écran permettant de créer une nouvelle réservation :

![New reservation](/img-readme/newReservation.PNG)

## Données de base

Lors de la première utilisation de l'application, la classe "src\Util\DataSet.java" sera appelée par l'application pour remplir la base de données.

### Rôles disponible par défaut

Un rôle permet de catégoriser le poste occupé par un employé dans l'hôtel. L'access level est une donnée utilisée pour savoir si un employé a le droit d'accéder à certaines fonctionnalités ou non dans l'application. 3 rôles sont ajoutés par défaut :

| Rôle | Access Level |
| ---- | ------------ |
| Réceptionniste  | 1  |
| Manager  | 2  |
| Directeur  | 3  |

### Employés créés par défaut

Un compte employé pour chaque rôle est créé par défaut :

| Identifiant | Prénom | Nom | Rôle |
| ----------- | ------ | --- | ---- |
| Max | Max | Parsons | Directeur |
| Jennifer | Jennifer | Arnold | Manager |
| Earnest | Earnest | Holmes | Réceptionniste |

On peut accéder à l'application en s'y connectant avec l'un de ces comptes (identifiant + mot de passe qui est "test" par défaut).

<b>Remarque :</b> Un employé avec un access level "1" (le réceptionniste ici) n'a pas accès au menu "Employés" destiné à la gestion des employés. Dans le menu "Chambres", il ne pourra pas créer ou modifier une chambre.

## Installation

Il est préconisé de créer un projet depuis des sources existantes (directement avec le lien du repository GitHub ou avec le projet téléchargé en zip puis décompressé) avec un IDE car celui-ci va détecter tout seul les librairies nécessaires au fonctionnement de l'application du dossier **`lib/`**. 
Vérifier que ces librairies sont bien liées au projet dans les options du projet.

S'assurer que le serveur MySQL est bien lancé. Sinon le lancer.

Ouvrir le fichier **`src/hibernate.cfg.xml`** et y renseigner un utilisateur (qui aura les droits sur la base de données HotelManager) et son mot de passe.

```
<property name="hibernate.connection.username"><!-- user --></property>
<property name="hibernate.connection.password"><!-- your_password --></property>
```

Compiler puis exécuter.

