import pandas as pd
import numpy as np
import math
import matplotlib.pyplot as plt

txt = open('StatsData.txt')

saveCount = int(txt.readline())
hideCount = int(txt.readline())

txt.close()

print(saveCount)
print(hideCount)

tabelaPlayers = pd.read_excel(r'dadosOutros.xlsx')
print(tabelaPlayers.keys())

mSaveOutros = tabelaPlayers['Saves de Outros Jogadores'].sort_values().mean()
mHideOutros = tabelaPlayers['Vezes que outros jogadores se esconderam'].sort_values().mean()

pessoas = ['Jogador', 'Média Outros Jogadores']
freq_absSaves = [saveCount, mSaveOutros]
freq_absHide = [hideCount, mHideOutros]

x = np.arange(len(pessoas))
width = 0.35
fig, ax = plt.subplots()

rects1 = ax.bar(x - width / 2, freq_absSaves, width, label='Salvar')
rects2 = ax.bar(x + width / 2, freq_absHide, width, label='Esconder')

ax.set_ylabel('Quantidade')
ax.set_title("Quantidade de vezes que salvou o jogo e se escondeu da vilã")
ax.set_xticks(x)
ax.set_xticklabels(pessoas)
ax.legend()

ax.bar_label(rects1, padding=3)
ax.bar_label(rects2, padding=3)

fig.tight_layout()

plt.show()
