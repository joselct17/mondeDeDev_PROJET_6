import {Theme} from "../../../theme/interfaces/theme.interface";

export interface ArticleResponse {
  id: number,
  name: string;
  content: string;
  date: Date;
  author: string;
  theme:string,
}
