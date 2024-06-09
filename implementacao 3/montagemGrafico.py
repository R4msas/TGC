import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

# Lista dos arquivos CSV
arquivos = ['a1.csv', 'a2.csv', 'b1.csv', 'b2.csv', 'c1.csv', 'c2.csv', 'd1.csv', 'd2.csv']

# Função para calcular a média ponderada
def weighted_mean(data):
    return np.average(data, weights=np.arange(1, len(data)+1))

# Lista para armazenar os resultados
resultados = []

for arquivo in arquivos:
    # Carrega o arquivo CSV
    df = pd.read_csv(arquivo)
    
    # Calcula as estatísticas
    media = df['tempo(ms)'].mean()
    mediana = df['tempo(ms)'].median()
    media_ponderada = weighted_mean(df['tempo(ms)'])
    

    # Obtém os valores das colunas relevantes
    num_vertices = df['numero de vertices'].iloc[0]  
    try:
        num_arestas = df['numero de arestas'].iloc[0]    
    except:
        num_arestas = df[' numero de arestas'].iloc[0]   
    
    num_caminhos_disjuntos = df['numero de caminhos disjuntos'].iloc[0]  # Supondo que é o mesmo para todas as linhas
    
    # Adiciona os resultados a lista
    resultados.append([arquivo, media, mediana, media_ponderada, num_vertices, num_arestas, num_caminhos_disjuntos])

# Converte os resultados para um DataFrame
resultados_df = pd.DataFrame(resultados, columns=['Arquivo', 'Média', 'Mediana', 'Média Ponderada', 'Número de Vértices', 'Número de Arestas', 'Número de Caminhos Disjuntos'])

# Configurações do gráfico
plt.figure(figsize=(15, 10))
sns.set(style="whitegrid")

# Gráfico de barras para cada estatística de tempo
resultados_melted = pd.melt(resultados_df, id_vars=['Arquivo'], value_vars=['Média', 'Mediana', 'Média Ponderada'], 
                            var_name='Estatística', value_name='Tempo (ms)')

plt.subplot(3, 1, 1)
sns.barplot(x='Arquivo', y='Tempo (ms)', hue='Estatística', data=resultados_melted)
plt.title('Análise dos tempos (ms) para diferentes arquivos')
plt.xlabel('Arquivos')
plt.ylabel('Tempo (ms)')
plt.xticks(rotation=45)
plt.legend(title='Estatísticas')
plt.tight_layout()

# Gráfico de barras para número de vértices e arestas
plt.subplot(3, 1, 2)
sns.barplot(x='Arquivo', y='Número de Vértices', data=resultados_df, color='skyblue', label='Vértices')
sns.barplot(x='Arquivo', y='Número de Arestas', data=resultados_df, color='lightgreen', label='Arestas', alpha=0.7)
plt.title('Número de Vértices e Arestas para diferentes arquivos')
plt.xlabel('Arquivos')
plt.ylabel('Quantidade')
plt.xticks(rotation=45)
plt.legend(title='Elemento')
plt.tight_layout()

# Gráfico de barras para número de caminhos disjuntos
plt.subplot(3, 1, 3)
sns.barplot(x='Arquivo', y='Número de Caminhos Disjuntos', data=resultados_df, palette='pastel')
plt.title('Número de Caminhos Disjuntos para diferentes arquivos')
plt.xlabel('Arquivos')
plt.ylabel('Número de Caminhos Disjuntos')
plt.xticks(rotation=45)
plt.tight_layout()

plt.show()
