import { ISpid } from '@/shared/model/spid.model';

export interface IPid {
  id?: number;
  pid?: number | null;
  phone?: string | null;
  ci?: string | null;
  di?: string | null;
  spidLists?: ISpid[] | null;
}

export class Pid implements IPid {
  constructor(
    public id?: number,
    public pid?: number | null,
    public phone?: string | null,
    public ci?: string | null,
    public di?: string | null,
    public spidLists?: ISpid[] | null
  ) {}
}
