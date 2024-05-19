export interface ResultatResponse {
  currentPage: number
  totalPages: number
  pageSize: number
  resultatPrelevementDTOS: ResultatPrelevementDtos[]
}

export interface ResultatPrelevementDtos {
  id: number
  dateBA: string
  numeroBA: number
  conforme: boolean
  dateTA:string
  numeroTA:number
  detail:string
  idPersonne: number
  idPrelevement:any
  nomProduit: string
}
