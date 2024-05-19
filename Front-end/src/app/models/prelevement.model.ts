import {Produit} from "./produit.model";
export interface PrelevementResponse {
  currentPage: number
  totalPages: number
  pageSize: number
  prelevementDTOS: Prelevement []
}

export class Prelevement {
  id: number = 0
  dateProcesVerbal!: Date
  numeroProcesVerbal!: number
  typePrelevement!: string
  cadreControle!: string
  niveauPrel!: string
  laboDestination!: string
  dateEnvoie!: Date
  etatAvancement!: string
  idPersonne!: number
  produitDTO!: Produit
}
