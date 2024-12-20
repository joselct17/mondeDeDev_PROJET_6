import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Theme} from "../../interfaces/theme.interface";


@Component({
  selector: 'app-theme-card',
  templateUrl: './themeCard.component.html',
  styleUrls: ['./themeCard.component.css']
})
export class ThemeCardComponent {
  @Input() theme!:Theme;

  @Output() subscriptionToggle:EventEmitter<Theme> = new EventEmitter<Theme>();

  constructor() {
  }

  toggleSubscription(): void {
    this.subscriptionToggle.emit(this.theme);
  }
}
