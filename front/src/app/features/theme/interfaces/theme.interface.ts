import {Article} from "../../articles/interfaces/article.interface";
import {User} from "../../../interfaces/user.interface";

export interface Theme {
  id: number;
  name: string;
  description:string;
  isSubscribed?: boolean;
}
