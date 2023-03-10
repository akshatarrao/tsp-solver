import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TspAlgorithmsComponent } from './tsp-algorithms/tsp-algorithms.component';

const routes: Routes = [
  { path: '', component: TspAlgorithmsComponent },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
