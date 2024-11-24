import { Card } from "antd";

export type WalletCardProperties = {
  name: string;
};

function WalletCard(properties: WalletCardProperties) {
  return (
    <Card title={properties.name} extra="Go to wallet">
      <p>Expense: </p>
      <p>Income: </p>
    </Card>
  );
}

export default WalletCard;
