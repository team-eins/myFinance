export class Account {
    constructor(
        public id: number,
        public name: string,
        public transactionCount: number,
        public balance: number,
        public lastMonth: number
    ){}
}
