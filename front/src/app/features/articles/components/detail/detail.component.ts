import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Article } from '../../interfaces/article.interface';
import {ArticlesService} from "../../services/articles.service";
import {CommentairesService} from "../../services/commentaires.service";
import {CommentaireRequest} from "../../interfaces/api/commentaireRequest.interface";
import {Comment} from "@angular/compiler";
import {CommentaireResponse} from "../../interfaces/api/commentaireResponse.interface";
import {SessionService} from "../../../../services/session.service";

@Component({
  selector: 'app-article-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class ArticleDetailComponent implements OnInit {
  article: Article | null = null; // Contient les données de l'article
  comments:  { username: string; content: string }[] = [];
  newComment: string = '';

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticlesService,
    private commentaireService: CommentairesService,
    private sessionService:SessionService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id'); // Récupération de l'ID dans l'URL
    if (id) {
      this.loadArticleDetails(id); // Charger les détails de l'article
    }
  }

  loadArticleDetails(id: string): void {
    this.articleService.detail(id).subscribe((article:Article) => {
      this.article = article;
      this.comments = article.comments || []; // Exemple pour: Article | null récupérer les commentaires
    });
  }


  postComment(): void {
    if (!this.newComment.trim()) {
      alert('Le commentaire est vide');
      return;
    }

    const commentRequest: CommentaireRequest = {
      article_id: this.article!.id, // L'ID de l'article en tant que article_id
      user_id:this.sessionService.isLogged?this.sessionService.user!.id:0 ,
      message: this.newComment,
    };

    this.commentaireService.send(commentRequest).subscribe(
      (response: CommentaireResponse) => {
        // Ajouter le commentaire localement après succès
        this.comments.push({
          username: 'Vous', // Par exemple, le nom de l'utilisateur connecté
          content: this.newComment,
        });

        // Réinitialiser le champ de commentaire
        this.newComment = '';
        alert('Commentaire posté avec succès');
      },
      (error) => {
        alert('Erreur lors de l\'envoi du commentaire');
      }
    );
  }
}
