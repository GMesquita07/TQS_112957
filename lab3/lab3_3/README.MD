**Nota**: Resposta à alínea 'c'. As alíneas anteriores encontram-se implementadas no ```lab3_2_cars```, como suposto.


## 3.3 - Integration Test

### Alínea c): Vantagens e Desvantagens de usar uma Base de Dados (real)

**Vantagens:**

Ambiente mais realista → Permite testar o sistema nas mesmas condições da produção.

Validação das interações com a base de dados → Garante que queries SQL e conexões funcionam corretamente.

Simulação de cenários concorrenciais → Possibilita avaliar o comportamento com múltiplos acessos simultâneos.

Menos discrepâncias entre testes e produção → Reduz a probabilidade de falhas inesperadas após o deploy.

**Desvantagens:**

Execução mais demorada → Bases de dados reais costumam ser mais lentas do que soluções em memória.

Configuração adicional → Exige setup extra, como serviços externos ou containers para garantir disponibilidade.

Risco de dados residuais → Se os dados não forem limpos adequadamente, podem comprometer os resultados dos testes.

Dependência de infraestrutura → O ambiente de testes fica vinculado à disponibilidade da base de dados.

