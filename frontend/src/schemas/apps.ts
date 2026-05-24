
export interface AppDescripton {
    id: number
    name: string
    description: string
    storePath: string
    indexPath: string
}

export interface AppData {
    description: AppDescripton
    data: any
}