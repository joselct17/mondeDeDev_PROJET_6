import {Theme} from "../../theme/interfaces/theme.interface";

export interface Article {
	id: number,
	name: string,
	content: string,
	author: String,
  theme:Theme,
	created_at: Date,
	updated_at: Date
}
