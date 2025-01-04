import {Theme} from "../../theme/interfaces/theme.interface";

export interface Article {
	id: number,
	name: string,
	content: string,
	author: string,
  theme:Theme,
  comments: any[],
  datePosted: Date,
	updated_at: Date
}
